package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pessoa;
import model.Produto;
import model.Usuario;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-30T20:52:00", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Movimento.class)
public class Movimento_ { 

    public static volatile SingularAttribute<Movimento, Integer> idMovimento;
    public static volatile SingularAttribute<Movimento, Character> tipo;
    public static volatile SingularAttribute<Movimento, Pessoa> pessoa;
    public static volatile SingularAttribute<Movimento, Produto> produto;
    public static volatile SingularAttribute<Movimento, Usuario> usuario;
    public static volatile SingularAttribute<Movimento, Integer> quantidade;
    public static volatile SingularAttribute<Movimento, Float> valorUnitario;

}