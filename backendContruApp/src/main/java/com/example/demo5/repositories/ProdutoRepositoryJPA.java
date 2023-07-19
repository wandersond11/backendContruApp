package com.example.demo5.repositories;

import com.example.demo5.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepositoryJPA  extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria_Id(Long categoriaId);

    List<Produto> findByCategoria_Nome(String nome);

    List<Produto> findByNome(String nome);

    List<Produto> findByNomeAndMarca(String nome, String marca);


}
