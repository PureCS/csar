package org.qmul.csar.code.java.parse.statement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.qmul.csar.lang.TypeStatement;
import org.qmul.csar.lang.descriptors.ClassDescriptor;
import org.qmul.csar.lang.descriptors.VisibilityModifier;
import org.qmul.csar.util.StringUtils;
import org.qmul.csar.util.ToStringStyles;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A class type declaration.
 */
public class ClassStatement implements TypeStatement {

    private final ClassDescriptor descriptor;
    private final BlockStatement block;
    private final List<Annotation> annotations;

    public ClassStatement(ClassDescriptor descriptor, BlockStatement block, List<Annotation> annotations) {
        this.descriptor = descriptor;
        this.block = block;
        this.annotations = Collections.unmodifiableList(annotations);
    }

    public ClassDescriptor getDescriptor() {
        return descriptor;
    }

    public BlockStatement getBlock() {
        return block;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassStatement that = (ClassStatement) o;
        return Objects.equals(descriptor, that.descriptor)
                && Objects.equals(block, that.block)
                && Objects.equals(annotations, that.annotations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptor, block, annotations);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyles.SHORT_DEFAULT_STYLE)
                .append("descriptor", descriptor)
                .append("block", block)
                .append("annotations", annotations)
                .toString();
    }

    @Override
    public String toPseudoCode(int indentation) {
        StringBuilder builder = new StringBuilder();

        if (getAnnotations().size() > 0) {
            getAnnotations().forEach(annotation -> builder.append(annotation.toPseudoCode(indentation))
                    .append(System.lineSeparator()));
        }
        builder.append(StringUtils.indentation(indentation));

        if (descriptor.getVisibilityModifier().isPresent()
                && descriptor.getVisibilityModifier().get() != VisibilityModifier.PACKAGE_PRIVATE) {
            builder.append(descriptor.getVisibilityModifier().get().toPseudoCode()).append(" ");
        }

        StringUtils.append(builder, descriptor.getStaticModifier(), "static ");
        StringUtils.append(builder, descriptor.getFinalModifier(), "final ");
        StringUtils.append(builder, descriptor.getAbstractModifier(), "abstract ");
        StringUtils.append(builder, descriptor.getStrictfpModifier(), "strictfp ");
        StringUtils.append(builder, descriptor.getInner(), "(inner) ");
        StringUtils.append(builder, descriptor.getLocal(), "(local) ");
        StringUtils.append(builder, descriptor.getAnonymous(), "(anonymous) ");

        if (descriptor.getInterfaceModifier().isPresent() && descriptor.getInterfaceModifier().get()) {
            builder.append("interface ");
        } else {
            builder.append("class ");
        }
        builder.append(descriptor.getIdentifierName());

        if (descriptor.getTypeParameters().size() > 0) {
            builder.append("<").append(String.join(", ", descriptor.getTypeParameters())).append(">");
        }
        descriptor.getExtendedClass().ifPresent(ec -> builder.append(" extends ").append(ec));

        if (descriptor.getImplementedInterfaces().size() > 0) {
            builder.append(" implements(").append(String.join(", ", descriptor.getImplementedInterfaces())).append(")");
        }

        if (block.equals(BlockStatement.EMPTY)) {
            builder.append(" { }");
        } else {
            builder.append(" {")
                    .append(System.lineSeparator())
                    .append(block.toPseudoCode(indentation + 1))
                    .append(System.lineSeparator())
                    .append(StringUtils.indentation(indentation))
                    .append("}");
        }
        return builder.toString();
    }
}
