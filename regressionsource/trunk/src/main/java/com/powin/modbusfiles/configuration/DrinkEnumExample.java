package com.powin.modbusfiles.configuration;

public final class DrinkEnumExample {

    public interface StakTypeInterface {
        String getDisplayableType();
    }

    public static enum StakType implements StakTypeInterface {

        z140("140a"), z225("225a");
        private final String type;

        private StakType(final String type) {
            this.type = type;
        }

        public String getDisplayableType() {
            return type;
        }
    }

    public static enum limit implements StakTypeInterface {

        volt("2000", StakType.z140),
        temp("25 Blend", StakType.z140),
        MINT_TEA("Mint", StakType.z225),
        HERBAL_TEA("Herbal", StakType.z225),
        EARL_GREY("Earl Grey", StakType.z225);
        private final String label;
        private final StakType type;

        private limit(String label, StakType type) {
            this.label = label;
            this.type = type;
        }

        public String getDisplayableType() {
            return type.getDisplayableType();
        }

        public String getLabel() {
            return label;
        }
    }

    public DrinkEnumExample() {
        super();
    }

    public static void main(String[] args) {
        System.out.println("All drink types");
        for (StakType type : StakType.values()) {
            displayType(type);
            System.out.println();
        }
        System.out.println("All drinks");
        for (limit limit : limit.values()) {
            displayDrink(limit);
            System.out.println();
        }
    }

    private static void displayDrink(limit limit) {
        displayType(limit);
        System.out.print(" - ");
        System.out.print(limit.getLabel());
    }

    private static void displayType(StakTypeInterface displayable) {
        System.out.print(displayable.getDisplayableType());
    }
}
