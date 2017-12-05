package br.com.escolalura.escolalura.repositories;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.escolalura.escolalura.codecs.AlunoCodec;
import br.com.escolalura.escolalura.models.Aluno;

@Repository
public class AlunoRepository {
	
	
	public void salvar(Aluno aluno){
		
		//Recuperando um codec de Document que será utulizando na criação de AlunoCodec
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		//Instanciando a classe AlunoCodec
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		//Recuperando um registrador de codec para a inclusão do AlunoCodec além das opções já existentes no Mongo
		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
										CodecRegistries.fromCodecs(alunoCodec));
		//Utilizado na hora da criação da conexão com o banco para informar todos os codec necessários, incluindo o AlunoCodec
		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();
		
		MongoClient cliente = new MongoClient("localhost:27017",opcoes);
		MongoDatabase banco = cliente.getDatabase("test");
		MongoCollection<Aluno> alunos = banco.getCollection("alunos", Aluno.class);
		alunos.insertOne(aluno);
		cliente.close();
	}
}
