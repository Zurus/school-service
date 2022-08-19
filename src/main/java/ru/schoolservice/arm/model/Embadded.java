package ru.schoolservice.arm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonRootName("_embedded")
public class Embadded {

    @JsonProperty("users")
    @JsonUnwrapped
    public List<User> users;

//    @JsonProperty("_embedded")
//    public Users users;

}
