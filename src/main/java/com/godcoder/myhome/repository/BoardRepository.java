package com.godcoder.myhome.repository;

import com.godcoder.myhome.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.GeneratedValue;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
