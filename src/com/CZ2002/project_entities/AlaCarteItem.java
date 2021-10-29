package com.CZ2002.project_entities;

import com.CZ2002.project_enums.Type;

public class AlaCarteItem extends MenuItem{
    // Attributes
    private Type type;

    // Constructor
    public AlaCarteItem(String name, String description, double price, Type type){
        super(name, description, price);
        this.type = type;
    }

    // Mutators
    public void setType(Type type) {
        this.type = type;
    }

    // Accessors
    public Type getType() {
        return this.type;
    }
}
