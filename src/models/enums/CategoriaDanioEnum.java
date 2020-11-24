package models.enums;

public enum CategoriaDanioEnum {

    NORMAL("G1"),
    TEMPRANA("G2"),
    MODERADA("G3"),
    SEVERA("G4"),
    TERMINAL("G5");
    private final String nombre;

    CategoriaDanioEnum(String nombre) { this.nombre= nombre;}

    public static CategoriaDanioEnum fromInteger(int valor)
    {
        switch (valor)
        {
            case 1:
                return NORMAL;

            case 2:
                return TEMPRANA;

            case 3:
                return MODERADA;

            case 4:
                return SEVERA;
        }

        return TERMINAL;
    }

    public int getValor()
    {
        switch (nombre)
        {
            case "G1":
                return 1;

            case "G2":
                return 2;

            case "G3":
                return 3;

            case "G4":
                return 4;
        }

        return 5;
    }

    public String getNombre()
    {
        return nombre;
    }
}
