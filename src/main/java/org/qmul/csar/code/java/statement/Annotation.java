package org.qmul.csar.code.java.statement;

import org.qmul.csar.lang.Expression;
import org.qmul.csar.lang.Statement;
import org.qmul.csar.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Annotation implements Statement {

    private final String identifierName;
    private final Optional<Value> value;

    public Annotation(String identifierName, Optional<Value> value) {
        this.identifierName = identifierName;
        this.value = value;
    }

    public String getIdentifierName() {
        return identifierName;
    }

    public Optional<Value> getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annotation that = (Annotation) o;
        return Objects.equals(identifierName, that.identifierName)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifierName, value);
    }

    @Override
    public String toString() {
        return String.format("Annotation{identifierName='%s', value=%s}", identifierName, value);
    }

    @Override
    public String toPseudoCode(int indentation) {
        String args = value.map(v -> "(" + v.toPseudoCode() + ")").orElse("");
        return StringUtils.indentation(indentation) + "@" + identifierName + args;
    }

    public interface Value extends Statement {
    }

    public static class ExpressionValue implements Value {

        private final String identifierName;
        private final Expression value;

        public ExpressionValue(String identifierName, Expression value) {
            this.identifierName = identifierName;
            this.value = value;
        }

        public String getIdentifierName() {
            return identifierName;
        }

        public Expression getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExpressionValue that = (ExpressionValue) o;
            return Objects.equals(identifierName, that.identifierName)
                    && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifierName, value);
        }

        @Override
        public String toString() {
            return String.format("Value{identifierName=%s, value=%s}", identifierName, value);
        }

        @Override
        public String toPseudoCode(int indentation) {
            return String.format("%s%s = %s", StringUtils.indentation(indentation), identifierName,
                    value.toPseudoCode());
        }
    }

    public static class AnnotationValue implements Value {

        private final String identifierName;
        private final Annotation value;

        public AnnotationValue(String identifierName, Annotation value) {
            this.identifierName = identifierName;
            this.value = value;
        }

        public String getIdentifierName() {
            return identifierName;
        }

        public Statement getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnnotationValue that = (AnnotationValue) o;
            return Objects.equals(identifierName, that.identifierName)
                    && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifierName, value);
        }

        @Override
        public String toString() {
            return String.format("Value{identifierName=%s, value=%s}", identifierName, value);
        }

        @Override
        public String toPseudoCode(int indentation) {
            return String.format("%s%s = %s", StringUtils.indentation(indentation), identifierName,
                    value.toPseudoCode());
        }
    }

    public static class StatementValues implements Value {

        private final String identifierName;
        private final List<Value> values;

        public StatementValues(String identifierName, List<Value> values) {
            this.identifierName = identifierName;
            this.values = Collections.unmodifiableList(values);
        }

        public String getIdentifierName() {
            return identifierName;
        }

        public List<Value> getValues() {
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StatementValues that = (StatementValues) o;
            return Objects.equals(identifierName, that.identifierName)
                    && Objects.equals(values, that.values);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifierName, values);
        }

        @Override
        public String toString() {
            return String.format("ValueList{identifierName=%s, values=%s}", identifierName, values);
        }

        @Override
        public String toPseudoCode(int indentation) {
            return "value_list"; // TODO write
        }
    }
}