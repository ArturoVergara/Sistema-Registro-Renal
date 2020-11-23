package models.enums;

public enum PrevisionEnum
{
    FONASA("Fonasa"),     //0
    ISAPRE("Isapre"),     //1
    CAPREDENA("Capredena");  //2 FUNAO

    private final String nombre;

    private PrevisionEnum(String nombre)
    {
        this.nombre = nombre;
    }

    public static PrevisionEnum fromInteger(int valor)
    {
        switch (valor)
        {
            case 1:
                return ISAPRE;

            case 2:
                return CAPREDENA;
        }

        return FONASA;
    }

    public int getValor()
    {
        switch (nombre)
        {
            case "Isapre":
                return 1;

            case "Capredena":
                return 2;
        }

        return 0;
    }

    public String getNombre()
    {
        return nombre;
    }
}
