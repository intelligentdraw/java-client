package com.laotek.intelligentdraw.dataserver.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.laotek.intelligentdraw.dataserver.doc.Diagram;

@Deprecated
public interface DiagramRepository extends MongoRepository<Diagram, String> {

}
