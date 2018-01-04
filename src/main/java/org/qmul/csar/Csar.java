package org.qmul.csar;

import org.qmul.csar.code.parse.ProjectCodeParser;
import org.qmul.csar.code.postprocess.CodeAnalysisUtils;
import org.qmul.csar.code.search.ProjectCodeSearcher;
import org.qmul.csar.lang.Statement;
import org.qmul.csar.query.CsarQuery;
import org.qmul.csar.query.CsarQueryFactory;
import org.qmul.csar.result.Result;
import org.qmul.csar.result.ResultFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * A code search and refactor tool instance.
 */
public class Csar {

    private static final Logger LOGGER = LoggerFactory.getLogger(Csar.class);
    private final String query;
    private final ProjectCodeParser parser;
    private final ResultFormatter resultFormatter;
    private final ProjectCodeSearcher searcher;
    private CsarQuery csarQuery;
    private Map<Path, Statement> code;
    private List<Result> results;
    private CodeAnalysisUtils codeAnalysisUtils;

    /**
     * Constructs a new {@link Csar} with the given arguments.
     *
     * @param query the csar query to perform
     * @param parser the project code parser to use
     * @param searcher the project searcher to use
     * @param resultFormatter the result formatter to use
     * @param codeAnalysisUtils the code analysis utils to use
     */
    public Csar(String query, ProjectCodeParser parser, ProjectCodeSearcher searcher, ResultFormatter resultFormatter,
            CodeAnalysisUtils codeAnalysisUtils) {
        this.query = query;
        this.parser = parser;
        this.searcher = searcher;
        this.resultFormatter = resultFormatter;
        this.codeAnalysisUtils = codeAnalysisUtils;
    }

    /**
     * Returns <tt>true</tt> if {@link #query} was parsed and assigned to {@link #csarQuery} successfully.
     * @return <tt>true</tt> if the query was parsed successfully.
     */
    public boolean parseQuery() {
        LOGGER.trace("Parsing query...");

        try {
            csarQuery = CsarQueryFactory.parse(query);
        } catch (Exception ex) {
            LOGGER.error("Failed to parse csar query because {}", ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Returns <tt>true</tt> if the code was parsed and the results assigned to {@link #code} successfully.
     * @return <tt>true</tt> if the code was parsed successfully.
     */
    public boolean parseCode() {
        LOGGER.trace("Parsing code...");
        code = parser.results();
        return !parser.errorOccurred();
    }

    public void postProcess() {
        codeAnalysisUtils.setCode(code);
        codeAnalysisUtils.analyze();
    }

    public boolean searchCode() {
        LOGGER.trace("Searching code...");
        searcher.setCsarQuery(csarQuery);
        searcher.setIterator(code.entrySet().iterator());
        results = searcher.results();
        return !searcher.errorOccurred();
    }

    public void printResults() {
        try {
            LOGGER.info("Search results:");
            LOGGER.info(resultFormatter.format(results));
        } catch (Exception ex) {
            LOGGER.error("error formatting search results: " + ex.getMessage());
        }
    }
}
