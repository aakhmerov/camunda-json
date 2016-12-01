package be.ecornely;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@Named
@Transactional(value=TxType.REQUIRES_NEW)
public class LogDemo implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    try {
      ArrayNode demo = (ArrayNode) execution.getVariable("demo");
      LoggerFactory.getLogger(this.getClass()).info("The received demo was : {}", demo);
      System.out.printf("Demo : %s %n", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(demo));
    } catch (Exception e) {
      LoggerFactory.getLogger(this.getClass()).error("Impossible to log demo", e);
      Object demoObject = execution.getVariable("demo");
      if(demoObject!=null){
        LoggerFactory.getLogger(this.getClass()).warn("The received demo was of type: {}", demoObject.getClass());
      }
    }
  }

}
