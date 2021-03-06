package org.qmul.csar.query;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public interface ContainsQueryElement {

    /**
     * A target descriptor element.
     */
    class TargetDescriptor implements ContainsQueryElement {
    
        private final org.qmul.csar.query.TargetDescriptor targetDescriptor;
    
        public TargetDescriptor(org.qmul.csar.query.TargetDescriptor targetDescriptor) {
            this.targetDescriptor = targetDescriptor;
        }
    
        public org.qmul.csar.query.TargetDescriptor getTargetDescriptor() {
            return targetDescriptor;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TargetDescriptor that = (TargetDescriptor) o;
            return Objects.equals(targetDescriptor, that.targetDescriptor);
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(targetDescriptor);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("targetDescriptor", targetDescriptor)
                    .toString();
        }
    }

    /**
     * A logical operator element. The order of precedence is (highest to lowest): NOT, AND, OR.
     */
    class LogicalOperator implements ContainsQueryElement {
    
        private final org.qmul.csar.query.LogicalOperator logicalOperator;
    
        public LogicalOperator(org.qmul.csar.query.LogicalOperator logicalOperator) {
            this.logicalOperator = logicalOperator;
        }
    
        public org.qmul.csar.query.LogicalOperator getLogicalOperator() {
            return logicalOperator;
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogicalOperator that = (LogicalOperator) o;
            return logicalOperator == that.logicalOperator;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(logicalOperator);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("logicalOperator", logicalOperator)
                    .toString();
        }
    }
}
