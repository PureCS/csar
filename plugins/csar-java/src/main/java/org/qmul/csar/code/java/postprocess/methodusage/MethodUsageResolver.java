package org.qmul.csar.code.java.postprocess.methodusage;

import org.qmul.csar.code.CodePostProcessor;
import org.qmul.csar.code.java.postprocess.typehierarchy.TypeHierarchyResolver;
import org.qmul.csar.lang.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Map;

/**
 * This maps method usages to their corresponding definitions. It requires {@link TypeHierarchyResolver} and
 * {@link org.qmul.csar.code.java.postprocess.methodproc.MethodProcessor} to be run first.
 */
public class MethodUsageResolver implements CodePostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUsageResolver.class);
    private TypeHierarchyResolver typeHierarchyResolver;

    public void postprocess(Map<Path, Statement> code) {
        LOGGER.info("Starting...");
        long startTime = System.currentTimeMillis();

        for (Map.Entry<Path, Statement> file : code.entrySet()) {
            Path path = file.getKey();
            Statement statement = file.getValue();

            MethodUsageStatementVisitor visitor = new MethodUsageStatementVisitor(code, path, typeHierarchyResolver);
            visitor.visitStatement(statement);
        }

        // Log completion message
        LOGGER.debug("Time Taken: {}ms", (System.currentTimeMillis() - startTime));
        LOGGER.info("Finished");
    }

    public void setTypeHierarchyResolver(TypeHierarchyResolver typeHierarchyResolver) {
        this.typeHierarchyResolver = typeHierarchyResolver;
    }
}
