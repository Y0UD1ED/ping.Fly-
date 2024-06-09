import ast


with open("logs.txt", "r",encoding='utf-8') as data:
    dict = ast.literal_eval(data.read())
    for i in dict:
        if 'Температура' in i:
            print(i)
