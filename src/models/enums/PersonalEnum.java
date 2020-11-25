package models.enums;

public enum PersonalEnum {
    ADMIN("Admin"),
    DOCTOR("Doctor"),
    LABORATORISTA("Laboratorista"),
    ENFERMERO("Enfermero"),
    GES("Ges");

    private final String nombre;

    PersonalEnum(String nombre) { this.nombre= nombre;}

    public static PersonalEnum fromInteger(int valor)
    {
        switch (valor)
        {
            case 1:
               return ADMIN;

            case 2:
                return DOCTOR;

            case 3:
                return LABORATORISTA;

            case 4:
                return ENFERMERO;
        }

        return GES;
    }

    public int getValor()
    {
        switch (nombre)
        {
            case "Admin":
                return 1;

            case "Doctor":
                return 2;

            case "Laboratorista":
                return 3;

            case "Enfermero":
                return 4;
        }

        return 5;
    }

    public String getNombre()
    {
        return nombre;
    }
}
