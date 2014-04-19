resolvers += Resolver.url("bintray-sbt-plugin-releases",
             url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers ++= Seq(
  "typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  "sbt-plugin-releases2" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/",
  "softprops-maven" at "http://dl.bintray.com/content/softprops/maven"
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")


