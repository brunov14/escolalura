package br.com.escolalura.escolalura.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.escolalura.escolalura.codecs.AlunoCodec;
import br.com.escolalura.escolalura.models.Aluno;
import br.com.escolalura.escolalura.models.Habilidade;

@Repository
public class AlunoRepository {
	
	private MongoClient cliente;
	private MongoDatabase banco;
	
	public void criarConexao(){
		//Recuperando um codec de Document que será utulizando na criação de AlunoCodec
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		//Instanciando a classe AlunoCodec
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		//Recuperando um registrador de codec para a inclusão do AlunoCodec além das opções já existentes no Mongo
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
										CodecRegistries.fromCodecs(alunoCodec));
		//Utilizado na hora da criação da conexão com o banco para informar todos os codec necessários, incluindo o AlunoCodec
		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();
		
		this.cliente = new MongoClient("localhost:27017",opcoes);
		this.banco = cliente.getDatabase("test");
		
	}
	
	
	public void salvar(Aluno aluno){
		criarConexao();
		MongoCollection<Aluno> alunos = this.banco.getCollection("alunos", Aluno.class);
		
		if(aluno.getId() == null)
			alunos.insertOne(aluno);
		else
			alunos.updateOne(Filters.eq("_id",aluno.getId()), new Document("$set",aluno));
		
		this.cliente.close();
	}
	
	public List<Aluno> obterTodosAlunos(){
		criarConexao();
		MongoCollection<Aluno> alunos = this.banco.getCollection("alunos", Aluno.class);
		MongoCursor<Aluno> resultado = alunos.find().iterator();
		
		List<Aluno> alunosEncontrados = new ArrayList<Aluno>();
		
		while (resultado.hasNext()) {
			alunosEncontrados.add(resultado.next());
		}
		
		return alunosEncontrados;
	}


	public Aluno obterAlunoPor(String id) {
		criarConexao();
		
		MongoCollection<Aluno> alunos = banco.getCollection("alunos",Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id",new ObjectId(id))).first();		
		
		return aluno;
	}

	
}
