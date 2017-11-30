package ru.shakirov.repository.dto;

public class SubjectDto {
    private short id;
    private String name;
    private boolean selected;

    public SubjectDto() {
    }

    public SubjectDto(short id, String name) {
        this.id = id;
        this.name = name;
        this.selected = false;
    }

    public SubjectDto(short id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectDto that = (SubjectDto) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

}
