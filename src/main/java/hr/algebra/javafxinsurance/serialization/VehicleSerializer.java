package hr.algebra.javafxinsurance.serialization;

import hr.algebra.javafxinsurance.dto.VehicleInfoDTO;
import hr.algebra.javafxinsurance.serialization.exceptions.NonSerializableClassException;

public class VehicleSerializer extends Serializer<VehicleInfoDTO>{
    public static final String SERIALIZATION_FOR_AUTHENTICATED_VEHICLE_LOCATION="data/authenticated-vehicle.ser";

    @Override
    public void save(VehicleInfoDTO classToSave) throws NonSerializableClassException {
        this.save(classToSave, SERIALIZATION_FOR_AUTHENTICATED_VEHICLE_LOCATION);
    }

    @Override
    public VehicleInfoDTO load() throws NonSerializableClassException {
        return load(SERIALIZATION_FOR_AUTHENTICATED_VEHICLE_LOCATION);
    }
}
