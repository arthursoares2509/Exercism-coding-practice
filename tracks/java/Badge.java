class Badge {
    public String print(Integer id, String name, String department) {
        String department_label = department == null ? "OWNER" : department.toUpperCase();

        if (id == null) {
            return name + " - " + department_label;
        }

        return "[" + id + "] - " + name + " - " + department_label;
    }
}