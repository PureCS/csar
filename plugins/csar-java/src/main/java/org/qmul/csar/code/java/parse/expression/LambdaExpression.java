package org.qmul.csar.code.java.parse.expression;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.qmul.csar.code.java.parse.statement.BlockStatement;
import org.qmul.csar.code.java.parse.statement.ExpressionStatement;
import org.qmul.csar.lang.Expression;
import org.qmul.csar.lang.Statement;
import org.qmul.csar.util.StringUtils;
import org.qmul.csar.util.ToStringStyles;

import java.util.Objects;

public class LambdaExpression implements Expression {

    private final LambdaParameter parameter;
    private final Statement value;

    public LambdaExpression(LambdaParameter parameter, ExpressionStatement value) {
        this.parameter = parameter;
        this.value = value;
    }

    public LambdaExpression(LambdaParameter parameter, BlockStatement value) {
        this.parameter = parameter;
        this.value = value;
    }

    public LambdaParameter getParameter() {
        return parameter;
    }

    public Statement getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LambdaExpression that = (LambdaExpression) o;
        return Objects.equals(parameter, that.parameter) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyles.SHORT_DEFAULT_STYLE)
                .append("parameter", parameter)
                .append("value", value)
                .toString();
    }

    @Override
    public String toPseudoCode(int indentation) {
        String i = StringUtils.indentation(indentation);

        if (value instanceof ExpressionStatement) {
            return String.format("%s(%s) -> %s", i, parameter.toPseudoCode(), value.toPseudoCode());
        } else {
            return String.format("%s(%s) -> {%s%s%s%s}", i, parameter.toPseudoCode(), System.lineSeparator(),
                    value.toPseudoCode(indentation + 1), System.lineSeparator(), i);
        }
    }
}
