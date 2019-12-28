package com.linux.demo.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private ObjectId id;
    // same as @NotBlank just for lombok
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Role role;
    @NonNull
    private boolean enabled;

    public User(Document doc) {
        this.id = doc.getObjectId("_id");
        this.username = doc.getString("username");
        this.password = doc.getString("password");
        this.role = Role.valueOf(doc.getString("role"));
        this.enabled = doc.getBoolean("enabled");
    }

    public Document toDoc() {
        Document doc = new Document();
        doc.put("username", this.username);
        doc.put("password", this.password);
        doc.put("enabled", this.enabled);
        doc.put("role", this.role.name());
        return doc;
    }

}
