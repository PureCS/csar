package org.qmul.csar.code.java.search;

import org.qmul.csar.code.RefactorTarget;
import org.qmul.csar.code.java.parse.expression.MethodCallExpression;
import org.qmul.csar.code.java.parse.statement.MethodStatement;
import org.qmul.csar.lang.Statement;
import org.qmul.csar.query.CsarQuery;
import org.qmul.csar.query.TargetDescriptor;
import org.qmul.csar.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A method use searcher.
 */
public class MethodUseSearcher implements Searcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUseSearcher.class);

    @Override
    public Result search(CsarQuery query, Path file, Statement statement) {
        TargetDescriptor targetDescriptor = query.getSearchTarget();

        // Search
        SearchStatementVisitor visitor = new SearchStatementVisitor(targetDescriptor);
        visitor.visitStatement(statement);

        // Aggregate and return results
        List<org.qmul.csar.result.Result> results = new ArrayList<>();
        List<RefactorTarget> refactorTargets = new ArrayList<>();

        for (Statement st : visitor.getResults()) {
            MethodStatement method = (MethodStatement)st;

            // Create Results
            List<org.qmul.csar.result.Result> tmpResults = method.getMethodUsages().stream()
                    .filter(expr -> !skipped(query, expr.getPath()))
                    .map(this::exprToResult)
                    .collect(Collectors.toList());
            results.addAll(tmpResults);

            // Create RefactorTargets (these are not restricted by search domain, or the output would be incorrect)
            List<RefactorTarget> tmpRefactorTargets = method.getMethodUsages().stream()
                    .map(RefactorTarget.Expression::new)
                    .collect(Collectors.toList());
            refactorTargets.add(new RefactorTarget.Statement(method));
            refactorTargets.addAll(tmpRefactorTargets);
        }
        return new Result(results, refactorTargets);
    }

    /**
     * Returns if the current file should be skipped - this handles the fromQuery.
     */
    private boolean skipped(CsarQuery query, Path file) {
        if (query.getFromTarget().size() == 0)
            return false;

        // From Query
        String fileNameWithoutExt = StringUtils.fileNameWithoutExtension(file);

        for (String fromDomain : query.getFromTarget()) {
            if (fromDomain.equals(fileNameWithoutExt)) {
                LOGGER.trace("Accepted: {}", fileNameWithoutExt);
                return false;
            }
        }
        LOGGER.trace("Skipped: {}", file);
        return true;
    }

    private org.qmul.csar.result.Result exprToResult(MethodCallExpression expr) {
        return new org.qmul.csar.result.Result(expr.getPath(), expr.getLineNumber(), expr.toPseudoCode());
    }
}

