package org.qmul.csar.code.java.postprocess.methodproc;

import org.qmul.csar.code.CodePostProcessor;
import org.qmul.csar.code.java.postprocess.qualifiedname.QualifiedNameResolver;
import org.qmul.csar.lang.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Map;

public class MethodProcessor implements CodePostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodProcessor.class);
    private QualifiedNameResolver qualifiedNameResolver = new QualifiedNameResolver();

    public void postprocess(Map<Path, Statement> code) {
        LOGGER.info("Starting...");
        long startTime = System.currentTimeMillis();

        for (Map.Entry<Path, Statement> file : code.entrySet()) {
            Path path = file.getKey();
            Statement statement = file.getValue();

            CompilationUnitVisitor visitor = new CompilationUnitVisitor(code, path, qualifiedNameResolver);
            visitor.visitStatement(statement);
        }

        // Log completion message
        LOGGER.debug("Time Taken: {}ms", (System.currentTimeMillis() - startTime));
        LOGGER.info("Finished");
    }
}
