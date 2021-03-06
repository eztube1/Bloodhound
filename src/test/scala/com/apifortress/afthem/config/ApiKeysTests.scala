/**
  * Copyright 2019 API Fortress
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  *
  * @author Simone Pezzano
  */
package com.apifortress.afthem.config

import org.junit.Test
import org.junit.Assert._
class ApiKeysTests {


  @Test
  def testApiKey() : Unit = {
    val keys = new ApiKeys(List(new ApiKey("123","abc",true)))
    assertEquals("abc",keys.getApiKey("123").get.appId)
  }

  @Test
  def testNoApiKey() : Unit = {
    val keys = new ApiKeys(List(new ApiKey("123","abc",true)))
    assertFalse(keys.getApiKey("111").isDefined)
  }

  @Test
  def testNoKeyProvided() : Unit = {
    val keys = new ApiKeys(List(new ApiKey("123","abc",true)))
    assertFalse(keys.getApiKey(null).isDefined)
  }
}
