
import json
import random
import string
import time


def newEntities(Etype, x, y):
    return {
        "type": Etype,
        "x" : x,
        "y": y,

    }


def save(dic, loging):
    name = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(10))
    name += str(time.time())
    with open(f'./src/test/resources/ExtraMap/{name}.json', 'w') as f:
        json.dump(dic, f, indent=2)
    log = open(f'./src/test/resources/ExtraMap/{name}.txt', 'w')
    log.write(loging)
    log.close()
    print(f"filename: {name}.json")
    # return name


def main():
    print("Add Entity, format type, x, y, [color, key]")
    entities = []
    log = ""
    while True:
        try:
            line = input()
            log += line + "\n"
            arg = line.split(",")
            if (len(arg) < 3):
                continue
            if (arg[0] in ['door', 'key', 'portal'] and len(arg) != 4):
                print("Error in input argument, lacking of 'color' in portal or 'key' in (door and key)")
                continue
            entity = newEntities(arg[0], int(arg[1]), int(arg[2]))
            if (arg[0] == "portal"):
                entity['colour'] = arg[3]
            elif (arg[0] in ['door', 'key']):
                entity['key'] = arg[3]
            # print(entity)
            entities.append(entity)
        except (ValueError, EOFError, KeyboardInterrupt):
            break
    response = {
        'entities': entities,
        'goal-condition': [],
    }
    save(response, log)


main()
