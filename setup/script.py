#/usr/bin/python3
import json
import firebase_admin
import urllib
from firebase_admin import firestore
from firebase_admin import credentials
from urllib.parse import quote
cred = credentials.Certificate('private_key.json')
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://trial-by-fire-6fb1d.firebaseio.com'
})
db = firestore.client()
movies_ref = db.collection(u'movies')
genres_ref = db.collection(u'genres')
actors_ref = db.collection(u'actors')

print(movies_ref.get())

with open('movies.json') as f:
    movies = json.load(f)
#
total = len(movies)
i = 0
for movie in movies[(total-10000):]:
    title = quote(movie['title'], safe='')
    movies_ref.document(title).set(movie)
    actors = movie['cast']
    for actor in actors:
        if actor == '':
            continue
        actor = quote(actor, safe='')
        try:
            l = actors_ref.document(actor).get().to_dict()
            if l is None:
                l = dict()
            l[title] = True
            actors_ref.document(actor).set(l)
        except ValueError:
            print('Problem with actor: '  + str(actor))
            continue
    genres = movie['genres']
    for genre in genres:
        try:
            if genre == '' or type(genre)!= str:
                continue
            # genre = quote(genre, safe='')
            l = genres_ref.document(genre).get().to_dict()
            if l is None:
                l = dict()
            l[title] = True
            if len(l) > 0:
                genres_ref.document(genre).set(l)
        except ValueError:
            continue
    i+=1
    if i%50 == 0:
        print("Uploaded  " + str(i) + " movies")

