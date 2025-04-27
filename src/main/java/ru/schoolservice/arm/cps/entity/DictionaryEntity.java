package ru.schoolservice.arm.cps.entity;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class DictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String code;
    @Column
    private String name;

    /**
     * Конструктор
     */
    public DictionaryEntity() {
    }

    /**
     * Копирующий конструктор
     *
     * @param other источник копирования
     */
    public DictionaryEntity(DictionaryEntity other) {
        this.id = other.id;
        this.code = other.code;
        this.name = other.name;
    }

    /**
     * Конструктор
     *
     * @param code код
     * @param name наименование
     */
    public DictionaryEntity(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Вернуть идентификатор
     *
     * @return идентификатор
     */
    public Long getId() {
        return id;
    }

    /**
     * Установить идентификатор
     *
     * @param id идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Вернуть код
     *
     * @return код
     */
    public String getCode() {
        return code;
    }

    /**
     * Установить код
     *
     * @param code код
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Вернуть наименование
     *
     * @return наименование
     */
    public String getName() {
        return name;
    }

    /**
     * Установить наименование
     *
     * @param name наименование
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        DictionaryEntity that = (DictionaryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "DictionaryEntity{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
