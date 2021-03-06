package org.qmul.csar.lang.descriptors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.qmul.csar.lang.Descriptor;
import org.qmul.csar.util.OptionalUtils;
import org.qmul.csar.util.ToStringStyles;

import java.util.Objects;
import java.util.Optional;

public class BlockCommentDescriptor extends AbstractCommentDescriptor {

    private final Optional<Boolean> javadoc;

    public BlockCommentDescriptor(Optional<String> content, Optional<Boolean> javadoc) {
        super(content);
        this.javadoc = javadoc;
    }

    public Optional<Boolean> getJavadoc() {
        return javadoc;
    }

    @Override
    public boolean lenientEquals(Descriptor o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlockCommentDescriptor that = (BlockCommentDescriptor) o;
        return OptionalUtils.lenientEquals(javadoc, that.javadoc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlockCommentDescriptor that = (BlockCommentDescriptor) o;
        return Objects.equals(javadoc, that.javadoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), javadoc);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyles.SHORT_DEFAULT_STYLE)
                .append("javadoc", javadoc)
                .toString();
    }
}
