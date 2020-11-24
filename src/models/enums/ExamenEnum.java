package models.enums;

public enum ExamenEnum {
    CREATININA("Creatinina"),
    UREA("Urea"),
    ALBUMINA("Albumina");
    private final String nombre;

    ExamenEnum(String nombre) { this.nombre= nombre;}

    public static ExamenEnum fromInteger(int valor)
    {
        switch (valor)
        {
            case 1:
                return CREATININA;

            case 2:
                return UREA;

        }
        return ALBUMINA;
    }

    public int getValor()
    {
        switch (nombre)
        {
            case "Creatinina":
                return 1;

            case "Urea":
                return 2;
        }

        return 3;
    }

    public String getNombre()
    {
        return nombre;
    }
}
