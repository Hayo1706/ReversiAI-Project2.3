package view;



import controller.Controller;

public interface View {
    public void setText(String text);
    public void UpdateGame(int size, Controller controller);
    public Controller getController();
    public void enable_pegs(int size);
    public void disable_pegs(int size);
}

