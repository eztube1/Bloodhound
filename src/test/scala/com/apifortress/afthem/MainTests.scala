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
package com.apifortress.afthem

import com.apifortress.afthem.actors.AppContext
import com.apifortress.afthem.config.loaders.YamlConfigLoader
import org.junit.Test
import org.springframework.context.ConfigurableApplicationContext

class MainTests {

  @Test
  def testMain() : Unit = {
    YamlConfigLoader.SUBPATH = "etc.test"
    Main.main(Array.empty[String])
    AppContext.springApplicationContext.asInstanceOf[ConfigurableApplicationContext].close()

  }
}
