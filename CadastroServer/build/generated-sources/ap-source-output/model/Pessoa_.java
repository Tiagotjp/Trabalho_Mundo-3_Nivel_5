package model;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Movimento;
import model.PessoaFisica;
import model.PessoaJuridica;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-30T20:52:00", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ { 

    public static volatile SingularAttribute<Pessoa, PessoaFisica> pessoaFisica;
    public static volatile SingularAttribute<Pessoa, Integer> idPessoa;
    public static volatile SingularAttribute<Pessoa, String> cidade;
    public static volatile SingularAttribute<Pessoa, String> estado;
    public static volatile SingularAttribute<Pessoa, String> telefone;
    public static volatile SingularAttribute<Pessoa, PessoaJuridica> pessoaJuridica;
    public static volatile SingularAttribute<Pessoa, String> logradouro;
    public static volatile SingularAttribute<Pessoa, String> nome;
    public static volatile SingularAttribute<Pessoa, String> email;
    public static volatile ListAttribute<Pessoa, Movimento> movimentoList;

}