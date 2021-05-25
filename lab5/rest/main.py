from flask import Flask, request, render_template
from flask_cors import CORS
import requests
import json

app = Flask(__name__)
CORS(app)


@app.route("/", methods=["POST"])
def pokemons():
    data, bad = get_pokemon_stats(request.form)
    return render_template("response.html", data=data, bad=bad)


def get_pokemon_stats(form):
    form_names = ["first", "second", "third", "fourth", "fifth", "sixth"]
    names = [form.get(name) for name in form_names]
    responses = [requests.get('https://pokeapi.co/api/v2/pokemon/' + name) for name in names if name]
    json_responses = [response.json() for response in responses if response.status_code != 404]
    good = len(json_responses)
    if good == 0:
        return {}, 6
    data = extract_data(json_responses)
    return compute(data, good), 6 - good


def extract_data(responses):
    return [create_data(response) for response in responses]


def create_data(pokemon):
    data = {
        "name": pokemon.get("name")
    }
    for stat in pokemon.get("stats"):
        data[stat.get("stat").get("name")] = stat.get("base_stat")
    return data


def pretty_print(data):
    print(json.dumps(data, indent=2))


def compute(data, good):
    new_data = {
        "Means": compute_means(data, good),
        "Max": compute_max(data),
        "Min": compute_min(data)
    }
    pretty_print(new_data)
    return new_data


def compute_means(data, good):
    return {
        "hp": compute_mean_stat(data, "hp", good),
        "attack": compute_mean_stat(data, "attack", good),
        "defense": compute_mean_stat(data, "defense", good),
        "special_attack": compute_mean_stat(data, "special-attack", good),
        "special_defense": compute_mean_stat(data, "special-defense", good),
        "speed": compute_mean_stat(data, "speed", good)
    }


def compute_mean_stat(data, name, good):
    return sum([pokemon.get(name) for pokemon in data])/good


def compute_max(data):
    return {
        "hp": compute_extremum(data, "hp", lambda x: max(x, key=lambda y: y['stat'])),
        "attack": compute_extremum(data, "attack", lambda x: max(x, key=lambda y: y['stat'])),
        "defense": compute_extremum(data, "defense", lambda x: max(x, key=lambda y: y['stat'])),
        "special_attack": compute_extremum(data, "special-attack", lambda x: max(x, key=lambda y: y['stat'])),
        "special_defense": compute_extremum(data, "special-defense", lambda x: max(x, key=lambda y: y['stat'])),
        "speed": compute_extremum(data, "speed", lambda x: max(x, key=lambda y: y['stat']))
    }


def compute_min(data):
    return {
        "hp": compute_extremum(data, "hp", lambda x: min(x, key=lambda y: y['stat'])),
        "attack": compute_extremum(data, "attack", lambda x: min(x, key=lambda y: y['stat'])),
        "defense": compute_extremum(data, "defense", lambda x: min(x, key=lambda y: y['stat'])),
        "special_attack": compute_extremum(data, "special-attack", lambda x: min(x, key=lambda y: y['stat'])),
        "special_defense": compute_extremum(data, "special-defense", lambda x: min(x, key=lambda y: y['stat'])),
        "speed": compute_extremum(data, "speed", lambda x: min(x, key=lambda y: y['stat']))
    }


def compute_extremum(data, name, func):
    return func([{"stat": pokemon.get(name), "name": pokemon.get("name")} for pokemon in data])


@app.route("/moves", methods=["POST"])
def get_pokemons_moves():
    pokemons = [name for name in request.form.get("names").split(",")]
    if pokemons == ['']:
        return render_template("moves.html", moves={}, bad="all")
    responses = {name: requests.get('https://pokeapi.co/api/v2/pokemon/' + name) for name in pokemons}
    json_responses = {name: response.json().get("moves") for name, response in responses.items() if response.status_code != 404}
    bad = len(pokemons) - len(json_responses)
    pretty_print(json_responses)
    return render_template("moves.html", moves=json_responses, bad=bad)


if __name__ == "__main__":
    app.run("127.0.0.1", 5000)
