package DataBase;

public class Tables {
    private final AvaliacaoDAO ava;
    private final ClienteDAO cli;
    private final ComidaDAO com;
    private final RestauranteDAO res;

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
