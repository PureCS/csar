package org.qmul.csar.code.java.statement;

import org.qmul.csar.lang.Expression;
import org.qmul.csar.lang.Statement;
import org.qmul.csar.util.StringUtils;

import java.util.Objects;

/**
 * A synchronized statement.
 */
public class SynchronizedStatement implements Statement {

    /**
     * This is a {@link org.qmul.csar.code.java.expression.ParenthesisExpression}, since the element to lock is
     * expressed within parentheses.
     */
    private final Expression element;
    private final BlockStatement block;

    public SynchronizedStatement(Expression element, BlockStatement block) {
        this.element = element;
        this.block = block;
    }

    public Expression getElement() {
        return element;
    }

    public BlockStatement getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SynchronizedStatement that = (SynchronizedStatement) o;
        return Objects.equals(element, that.element)
                && Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, block);
    }

    @Override
    public String toString() {
        return String.format("SynchronizedStatement{element=%s, block=%s}", element, block);
    }

    @Override
    public String toPseudoCode(int indentation) {
        return new StringBuilder()
                .append("synchronized(")
                .append(element.toPseudoCode())
                .append(") {")
                .append(block.toPseudoCode())
                .append(StringUtils.LINE_SEPARATOR)
                .append("}")
                .toString();
    }
}
