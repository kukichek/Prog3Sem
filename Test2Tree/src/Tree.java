package tree;

import java.io.ObjectOutputStream;
import java.util.Objects;

public abstract class Tree implements Comparable<Tree> {
    protected String name;
    protected Type type;
    protected int age;

    private enum Type {
        CONIFEROUS("хвойное"), DECIDUOUS("лиственное");
        private String description;

        Type(String name) {
            this.description = name;
        }
    }

    public Tree(String name, String type, int age) throws IncorrectInputDataException {
        if ((name == null) || (name.equals(""))) {
            throw new IncorrectInputDataException("Incorrect name");
        }
        if (age < 0) {
            throw new IncorrectInputDataException("Incorrect age");
        }

        this.name = name;
        this.age = age;

        switch (type) {
            case "хвойное":
                this.type = Type.CONIFEROUS;
                break;
            case "лиственное":
                this.type = Type.DECIDUOUS;
                break;
            default:
                throw new IncorrectInputDataException("Incorrect type");
        }
    }

    @Override
    public int compareTo(Tree tree) {
        int diff1 = name.compareTo(tree.name);
        if (diff1 == 0) {
            int diff2 = ((Integer) tree.age).compareTo(age);
            if (diff2 == 0) {
                return type.compareTo(tree.type);
            }
            return diff2;
        }
        return diff1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != getClass() || obj == null) return false;

        Tree someTree = (Tree) obj;
        return Objects.equals(name, someTree.name) &&
                Objects.equals(age, someTree.age) &&
                type == someTree.type;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(", ");
        stringBuilder.append(type.description).append(", ");
        stringBuilder.append(age).append(" лет");
        return stringBuilder.toString();
    }

    public abstract void print();
}
