package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "23 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            published = "20 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            published = "19 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
            published = "18 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "17 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "16 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0
        ),
        Post
            (
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 0,
            shares = 0,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),

        )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else
                if (!it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = ++it.likes) else
                    it.copy(likedByMe = !it.likedByMe, likes = --it.likes)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
    override fun getPost(id: Long) : Post {
        return posts.single { it.id == id }
    }
}
