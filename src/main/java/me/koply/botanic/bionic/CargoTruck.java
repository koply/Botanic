package me.koply.botanic.bionic;

public final class CargoTruck {

    private static BionicInfo delivery;
    public static BionicInfo getDelivery() {
        return delivery;
    }
    public static void setDelivery(BionicInfo info) {
        delivery = info;
    }
}