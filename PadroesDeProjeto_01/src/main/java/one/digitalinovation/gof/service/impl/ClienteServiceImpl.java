package one.digitalinovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import one.digitalinovation.gof.model.Cliente;
import one.digitalinovation.gof.model.ClienteRepository;
import one.digitalinovation.gof.model.Endereco;
import one.digitalinovation.gof.model.EnderecoRepository;

@Service
public class ClienteServiceImpl implements ClienteService{

	// SINGLETON: INJETAR OS COMPONENTES DO SPRING COM @AUTOWIRED.
		@Autowired
		private ClienteRepository clienteRepository;
		@Autowired
		private EnderecoRepository enderecoRepository;
		@Autowired
		private ViaCepService viaCepService;
		
		// STRATEGY: IMPLEMENTAR OS MÉTODOS DEFINIDOS NA INTERFACE.
		// FACADE: ABSTRAIR INTEGRAÇÕES COM SUBSISTEMAS, PROVENDO UMA INTERFACE SIMPLES.
		@Override
		public Iterable<Cliente> buscarTodos() {
			// BUSCAR TODOS OS CLIENTES.
			return clienteRepository.findAll();
		}

		@Override
		public Cliente buscarPorId(Long id) {
			// BUSCAR CLIENTE POR ID.
			Optional<Cliente> cliente = clienteRepository.findById(id);
			return cliente.get();
		}

		@Override
		public void inserir(Cliente cliente) {
			salvarClienteComCep(cliente);
		}

		@Override
		public void atualizar(Long id, Cliente cliente) {
			// BUSCAR CLIENTE POR ID, CASO EXISTA:
			Optional<Cliente> clienteBd = clienteRepository.findById(id);
			if (clienteBd.isPresent()) {
				salvarClienteComCep(cliente);
			}
		}

		@Override
		public void deletar(Long id) {
			// DELETAR CLIENTE POR ID.
			clienteRepository.deleteById(id);
		}

		private void salvarClienteComCep(Cliente cliente) {
			// VERIFICAR SE O ENDERECO DO CLIENTE JÁ EXISTE (PELO CEP).
			String cep = cliente.getEndereco().getCep();
			Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
				// CASO NÃO EXISTA, INTEGRAR COM O VIACEP E PERSISTIR O RETORNO.
				Endereco novoEndereco = viaCepService.consultarCep(cep);
				enderecoRepository.save(novoEndereco);
				return novoEndereco;
			});
			cliente.setEndereco(endereco);
			// INSERIR CLIENTE, VINCULANDO O ENDERECO (NOVO OU EXISTENTE).
			clienteRepository.save(cliente);
		}
}
