import java.io.File
import org.apache.spark.sql.SparkSession
import scala.reflect.io.Directory

object CC98 {
	def main(args: Array[String]) {
		new Directory(new File("output")).deleteRecursively()
		val sc = SparkSession.builder.appName("CC98").master("local").getOrCreate().sparkContext
		val lines = sc.textFile("input.txt").map(line=>line.toLowerCase)
		val words = lines.flatMap(l => l.split(" ")).filter(x=>x.length>0).map(w => (w, 1)).cache()
		val counts = words.reduceByKey(_ + _).sortByKey(true)
		counts.saveAsTextFile("output")
		println(words.count())
	}
}