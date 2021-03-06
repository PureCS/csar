package org.qmul.csar.code.java.parse.expression;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.qmul.csar.lang.Expression;
import org.qmul.csar.util.StringUtils;
import org.qmul.csar.util.ToStringStyles;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayInitializationExpression implements Expression {

    private final List<Expression> expressions;
    private final String typeName;

    public ArrayInitializationExpression(String typeName, List<Expression> expressions) {
        this.typeName = typeName;
        this.expressions = Collections.unmodifiableList(expressions);
    }

    public String getTypeName() {
        return typeName;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayInitializationExpression that = (ArrayInitializationExpression) o;
        return Objects.equals(typeName, that.typeName) && Objects.equals(expressions, that.expressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expressions);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyles.SHORT_DEFAULT_STYLE)
                .append("typeName", typeName)
                .append("expressions", expressions)
                .toString();
    }

    @Override
    public String toPseudoCode(int indentation) {
        return StringUtils.indentation(indentation)
                + expressions.stream().map(Expression::toPseudoCode).collect(Collectors.joining(" "));
    }
}
