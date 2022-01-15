package src.DataBase;

public class Tables {
    private AvaliacaoDAO ava;
    private ClienteDAO cli;
    private ComidaDAO com;
    private RestauranteDAO res;

    public Tables(AvaliacaoDAO ava, ClienteDAO cli, ComidaDAO com, RestauranteDAO res) {
        this.ava = ava;
        this.cli = cli;
        this.com = com;
        this.res = res;
    }

    public AvaliacaoDAO getAva() {
        return ava;
    }

    public ClienteDAO getCli() {
        return cli;
    }

    public ComidaDAO getCom() {
        return com;
    }

    public RestauranteDAO getRes() {
        return res;
    }
}
