package IO

import os.Path

val invalidPath :Vector[Path]= (os.list(os.pwd / os.RelPath("src"))).toVector

def isAnInvalidPath(path: Path) :Boolean =
  invalidPath.exists( path.startsWith(_))

@main def test() =
  println(isAnInvalidPath(os.pwd / "src" / "main"))
  println(invalidPath.map(_))