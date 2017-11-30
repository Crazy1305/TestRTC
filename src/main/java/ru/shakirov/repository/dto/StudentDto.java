package ru.shakirov.repository.dto;


public class StudentDto {

    private String name;

    public StudentDto() {
    }

    public StudentDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentDto that = (StudentDto) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }
}
