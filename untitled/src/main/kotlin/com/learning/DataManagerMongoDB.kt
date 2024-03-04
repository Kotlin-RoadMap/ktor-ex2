package com.learning

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory

class DataManagerMongoDB {
  val log = LoggerFactory.getLogger(DataManagerMongoDB::class.java)
  val database: MongoDatabase
  val bookCollection: MongoCollection<Book>

  init {
    val pojoCodecRegistry: CodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
    val codecRegistry: CodecRegistry = fromRegistries(
      MongoClientSettings.getDefaultCodecRegistry(),
      pojoCodecRegistry
    )

    val clientSettings = MongoClientSettings.builder()
      .codecRegistry(codecRegistry)
      .build()

    val mongoClient = MongoClients.create(clientSettings)
    database = mongoClient.getDatabase("development")
    bookCollection = database.getCollection(Book::class.java.simpleName, Book::class.java)
    initBooks()
  }

  fun initBooks() {
    bookCollection.insertOne(Book(null, "How to grow apples1", "Mr. Appleton", 50.00f))
    bookCollection.insertOne(Book(null, "How to grow apples2", "Mr. Orangeton", 11.00f))
    bookCollection.insertOne(Book(null, "How to grow apples3", "Mr. Lemon", 12.00f))
    bookCollection.insertOne(Book(null, "How to grow apples4", "Mr. Pineapple", 11.00f))
    bookCollection.insertOne(Book(null, "How to grow apples5", "Mr. Pears", 11.00f))
    bookCollection.insertOne(Book(null, "How to grow apples6", "Mr. Coconut", 22.00f))
    bookCollection.insertOne(Book(null, "How to grow apples7", "Mr. Appleton", 333.00f))
  }

  fun newBook(book: Book): Book {
    bookCollection.insertOne(book)
    return book
  }

  fun updateBook(book: Book): Book {
    val bookfound = bookCollection.find(Document("_id", book.id)).first()
    bookfound?.title = book.title
    bookfound?.author = book.author
    bookfound?.price = book.price
    return bookfound!!
  }

  fun deleteBook(bookid: String): Book {
    val bookfound = bookCollection.find(Document("_id", bookid)).first()
    bookCollection.deleteOne(eq("_id", ObjectId(bookid)))
    return bookfound!!
  }

  fun allBooks(): List<Book> {
    return bookCollection.find().toList()
  }

  fun sortedBooks(sortBy: String, asc: Boolean): List<Book> {
    val pageno = 1
    val pageSize = 1000
    val ascInt: Int = if (asc) 1 else -1
    return bookCollection
      .find()
      .sort(Document(mapOf(Pair(sortBy, ascInt), Pair("_id", -1))))
      .skip(pageno - 1)
      .limit(pageSize)
      .toList()
  }
}