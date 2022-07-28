/*
 * Copyright 2022 FOLIO Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.folio.authlete.util

trait Show[A] {
  def show(a: A): String
}

object Show {
  def apply[A: Show]: Show[A] = implicitly

  implicit final class ShowOps[A: Show](self: A) {

    def show: String = {
      if (self == null) {
        "null"
      } else {
        Show[A].show(self)
      }
    }
  }

  def fromToString[A]: Show[A] = _.toString

  implicit val showString: Show[String] = fromToString

  implicit val showInt: Show[Int] = fromToString

  implicit val showLong: Show[Long] = fromToString

  implicit def showIterable[A: Show]: Show[Iterable[A]] = { xs =>
    s"[${xs.map(Show[A].show).mkString(", ")}]"
  }

  implicit def showTuple2[A: Show, B: Show]: Show[(A, B)] = tup => s"(${tup._1}=${tup._2})"

  implicit def showSeq[A: Show]: Show[Seq[A]] = Show[Iterable[A]].show(_)

  implicit def showSet[A: Show]: Show[Set[A]] = Show[Iterable[A]].show(_)

  implicit def showMap[A: Show, B: Show]: Show[Map[A, B]] = map => {
    s"{${map.map { case (k, v) => s"""${k.show}=${v.show}""" }.mkString(", ")}}"
  }
}
