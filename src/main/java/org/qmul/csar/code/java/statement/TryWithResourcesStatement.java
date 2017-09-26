package org.qmul.csar.code.java.statement;

import java.util.List;
import java.util.Objects;

/**
 * A try with resources statement.
 */
public class TryWithResourcesStatement extends TryStatement {

    private final LocalVariableStatements resources;

    public TryWithResourcesStatement(BlockStatement block, List<CatchStatement> catches, BlockStatement finallyBlock,
            LocalVariableStatements resources) {
        super(block, catches, finallyBlock);
        this.resources = resources;
    }

    public LocalVariableStatements getResources() {
        return resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TryWithResourcesStatement that = (TryWithResourcesStatement) o;
        return Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), resources);
    }

    @Override
    public String toPseudoCode(int indentation) {
        return "try-with-res"; // TODO write
    }
}
