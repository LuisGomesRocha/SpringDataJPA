# SpringDataJPA

<h2 align="center"> Primeiro desafio: <a href="https://www.alura.com.br/curso-online-spring-data-jpa">🔗 Spring Data JPA: Repositórios, Consultas, Projeções e Specifications</a> </h2>


<p align="justify"> :robot: Precisamos possibilitar a inserção, atualização, exibição e remoção de uma avaliação criada por mentores(as). Além disso, vamos querer buscar as avaliações pesquisando pelos títulos delas. Descreva exatamente quais passos você faria para possibilitar que tais operações fossem feitas usando o Spring Data JPA aproveitando o máximo de coisas prontas da tecnologia. :robot: </p>


<p align="justify"> :exclamation:  Inicialmente vou criar minha classe Avaliação, adiciono os atributos necessários e também adiciono um id. Uso o @Entity em cima da classe e o @Id em cima do atributo id. @Entity explica para o Hibernate que aquela classe vai ser uma tabela no banco e o @Id explica que aquele atributo vai ser a chave primaria na tabela. Utilizo também @GeneratedValue(strategy = GenerationType.IDENTITY) para que o id seja incrementado de forma automática. Os outros atributos eu não preciso mapear porque ele já vai mapear para colunas do mesmo nome.:exclamation:  </p>

<p align="justify"> :exclamation:  Utilizando nos atributos as anotações: @NotBlank, @Size e @Column não permitindo que o atributo receba um valor vazio ou nulo, bem como deve possuir o mínimo e máximo de caracteres, sendo o valor inserido único no banco de dados, não sendo aceito caso seja repetido ("unique"). :exclamation:  </p>

```
@Entity
public class Avaliacao {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Título da avaliação em branco!")
	@Size(min = 3, max = 250, message = "Título deve conter de 3 a 250 caracteres!")
	@Column(unique = true)
	private String tituloAvaliacao;

	@NotBlank(message = "Corpo da avaliação em branco!")
	@Size(min = 3, max = 500, message = "Corpo deve conter de 3 a 500 caracteres!")
	@Column(unique = true)
	private String corpoAvaliacao;

	@NotBlank
	private TipoPerfil tipoPerfil;
```

<p align="justify"> :exclamation:  Vou criar em conjunto uma classe ennum que defina entre MENTORES e ALUNOS, podendo assim separar no momento de cadastro, permitindo apenas os mentores de criar as avaliações.:exclamation:</p>

```
package br.com.zup.SpringDataJPA.domain;

public enum TipoPerfil {
	MENTORES,
	ALUNOS
}
```
  
 
 - [x] Inserção, atualização, exibição e remoção de uma avaliação criada por mentores(as)
 
<p align="justify"> :exclamation:  Inicialmente os dados serão injetados pelo método POST pelo Postman no endereço mapeado no AvaliacaoController como "@RequestMapping(value = "/api/avaliacao")", a requisição será recebida pelo controller pela função cadastrarAvaliacao e atribuindo as características do objeto implementado pelo avaliacaoRequest (evitando que minha entidade descrita na classe Avaliacao seja exposta). Ainda no AvaliacaoController, teremos a injeção de dependencia AvaliacaoRepository através da anotação @Autowired, passando para a função "avaliacaoService.cadastrarAvaliacao(avaliacao)" que por sua vez vai utilizar o método "save" do avaliacaoRepository (que recebeu do JpaRepository os métodos HTTP), salvando os dados (uma vez validados), no banco de dados.:exclamation:  </p>


AvaliacaoRequest

```
public class AvaliacaoRequest {

	@NotBlank(message = "Título da avaliação em branco!")
	@Size(min = 3, max = 250, message = "Título deve conter de 3 a 250 caracteres!")
	@Column(unique = true)
	private String tituloAvaliacao;

	@NotBlank(message = "Corpo da avaliação em branco!")
	@Size(min = 3, max = 500, message = "Corpo deve conter de 3 a 500 caracteres!")
	@Column(unique = true)
	private String corpoAvaliacao;

	@NotBlank
	private TipoPerfil tipoPerfil;

	public Avaliacao toModel() {
		return new Avaliacao(this.tituloAvaliacao, this.corpoAvaliacao, this.tipoPerfil);
	}
```

AvaliacaoRepository

```
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	Optional<Avaliacao> findByTitulo(String titulo );

	Avaliacao save(Avaliacao avaliacao);
	

}
}
```
AvaliacaoService
```
public class AvaliacaoService {

	@Autowired
	AvaliacaoRepository avaliacaoRepository;
	
		
	// Cadastrar

	public Avaliacao cadastrarAvaliacao(Avaliacao avaliacao) throws Exception {

		return avaliacaoRepository.save(avaliacao);

	}

	// Buscar

		public Avaliacao buscarTitulo(String titulo) {

		return avaliacaoRepository.findByTitulo(titulo).get();

	}
	
	public Avaliacao buscarTudo() {

		return (Avaliacao) avaliacaoRepository.findAll();

	}

	public void remover(Long id) {
		avaliacaoRepository.deleteById(id);
	
	}

}

 ```
 
 AvaliacaoController

 ```
@RestController
@RequestMapping(value = "/api/avaliacao")
@Profile("prod")
public class AvaliacaoController {

	@Autowired
	AvaliacaoService avaliacaoService;

	// Criar Avaliação
	@PostMapping
	public ResponseEntity<AvaliacaoResponse> novaAvaliacao(@Validated @RequestBody AvaliacaoRequest avaliacaoRequest)
			throws Exception {

		Avaliacao avaliacao = avaliacaoRequest.toModel();
		avaliacaoService.cadastrarAvaliacao(avaliacao);
		AvaliacaoResponse avaliacaoResponse = avaliacao.toResponse();
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoResponse);

	}

	// Buscar Avaliação
	@GetMapping("/titulo")
	public ResponseEntity<AvaliacaoResponse> buscar(@PathVariable Long id) {
		String titulo = null;
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.buscarTitulo(titulo).toResponse());

	}

	// Listar Avaliação
	@GetMapping("/listar")
	@Cacheable(value = "buscarTudo")
	public ResponseEntity<Avaliacao> buscarTudo(@RequestParam int pagina, @RequestParam int qtd) {
		Pageable avaliacao = (Pageable) PageRequest.of(pagina, qtd);
		return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.buscarTudo());

	}

	// Deletar Avaliação
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Avaliacao> remover(@PathVariable Long id) {
		avaliacaoService.remover(id);
		return ResponseEntity.ok().build();

	}

}
 ```
 
 <p align="justify"> :robot: Como vamos assegurar que apenas mentores possam criar as avaliações?:robot: </p>
 
 <p align="justify"> :exclamation: Implementei uma classe denominada SecurityConfigurations.java que vai configurar a hierarquia de acessos em função do perfil selecionando através dos "hasRole("MENTOR")", limitando quais acessos/ações são permitidas.
:exclamation: </p>

 ```
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/cadastro/listar").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()		
		.antMatchers(HttpMethod.POST, "/api/avaliacao/**").hasRole("MENTOR")
			.anyRequest().authenticated()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, avaliacaoRepository), UsernamePasswordAuthenticationFilter.class);
	}
```

 <p align="justify"> :robot: Pergunta bônus: Se você só cria interface no Spring Data JPA, onde está a real implementação? :robot: </p>
 
<p align="justify"> :exclamation: Em tempo de execução, o Spring Data JPA cria uma implementação da interface possibilitando que nós possamos definir os comportamentos a serem implementados utilizando seus métodos extendidos. É feito uso da API de Reflection e manipulação de bytecode intensiva.:exclamation: </p>
  
 
<h2 align="center"> <img src="https://media1.tenor.com/images/668297a40fc37570d5ccb1c6e387608c/tenor.gif" width="230" height="200" /> </h2>


<p align="justify"> :exclamation:  Reflexão é um recurso da API Java que possibilita aos aplicativos o acesso e a modificação do comportamento de aplicações que estão rodando na Java Virtual Machine. Uma classe pode acessar outras classes em tempo de execução, sem conhecer sua definição no momento da compilação. Informações relativas à esta definição, como seus construtores, métodos e atributos, podem ser facilmente acessados através de métodos de reflexão da API Java. Classes externas à aplicação, que não foram compiladas junto a mesma, podem ser instanciadas para utilização de seus recursos. Os recursos de reflexão oferecidos pela API Java, na maioria dos casos, são utilizados para prover extensão de funcionalidades a aplicações, desenvolvimento de ferramentas de debug e aplicativos que permitem a navegação no conteúdo de classes compiladas.:exclamation: </p>
  

<h2 align="center"> <img src="https://media1.tenor.com/images/a17a19d0110638638c7b584379ed2b57/tenor.gif" width="230" height="200" /> </h2>


<p align="justify"> :exclamation: 

Spring Data JPA: Repositórios, Consultas, Projeções e Specifcations
Cenário:
Precisamos possibilitar a inserção, atualização, exibição e remoção de uma avaliação criada por mentores(as). Além disso, vamos querer buscar as avaliações pesquisando pelos títulos delas.
Descreva exatamente quais passos você faria para possibilitar que tais operações fossem feitas usando o Spring Data JPA aproveitando o máximo de coisas prontas da tecnologia.
Pergunta bônus: Se você só cria interface no Spring Data JPA, onde está a real implementação?

Aqui temos duas opçoes 

CrudRepository
CrudRepositoryé uma interface básica e estende a Repositoryinterface.
CrudRepository fornece principalmente operações CRUD (Criar, Ler, Atualizar, Excluir).
O tipo de saveAll()método de retorno é Iterable.
Caso de uso - para realizar operações CRUD, defina a extensão do repositório CrudRepository.
JpaRepository
JpaRepositoryestende PagingAndSortingRepositoryque estende CrudRepository.
JpaRepositoryfornece operações CRUD e paginação, juntamente com métodos adicionais, tais como flush(), saveAndFlush()e deleteInBatch(), etc.
O tipo de saveAll()método de retorno é a List.
Caso de uso - para executar operações CRUD e em lote, defina extensões de repositório JpaRepository.
  
:exclamation: </p>
