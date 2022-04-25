package com.github.wouterman.ruleengine;

public interface EvaluationResult {

  ObjectType getType();

  int getPrecedence();

}