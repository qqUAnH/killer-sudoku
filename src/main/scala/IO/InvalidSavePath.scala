package IO

import os.Path

val invalidPath :Vector[Path]= (os.list(os.pwd / os.RelPath("src"))
                        ++ os.list(os.pwd /os.RelPath("test"))).toVector
def isAnInvalidPath(path: Path) :Boolean =
  invalidPath.exists( path.startsWith(_))
