package hr.algebra.javafxinsurance.serialization;

import hr.algebra.javafxinsurance.model.Token;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;

public class TokenSerializer extends Serializer<Token>{
    private static final String SERIALIZATION_FOR_TOKEN_LOCATION="data/token.ser";

    @Override
    public void save(Token classToSave) throws NonSerializableClassException {
        this.save(classToSave, SERIALIZATION_FOR_TOKEN_LOCATION);
    }

    @Override
    public Token load() throws NonSerializableClassException {
        return load(SERIALIZATION_FOR_TOKEN_LOCATION);
    }
}
