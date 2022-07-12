
import json
import random
import string
import time



def print_input(func):
    def warper(*args, **kwargs):
        res = func(*args,  **kwargs)
        print("Input: ", res)
        return res
    return warper


@print_input
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
    print(f"filename: {name}")
    # return name

@print_input
def goal(target):
    return {
        'goal': target
    }

@print_input
def supergoal(rel, a, b):
    return {
        'goal': rel,
        'subgoals': [
            {'goal': a},
            {'goal': b},
        ]
    }

@print_input
def complexCreateGoal(temp, command):
    """
    x, a, and, a, b
    x, a, goal,
    """
    args = command.replace(" ", "").split(',')
    if (len(args) == 3):
        temp[args[1]] = goal(args[2])
    elif (len(args) == 5):
        temp[args[1]] = supergoal(args[2], args[3], args[4])
    return f"add temp as key {args[1]}"

@print_input
def complexMergeGoal(temp, command):
    """
    m, and, a, b
    """
    args = command.replace(" ", "").split(',')
    if (args[2] in temp.keys() and args[3] in temp.keys()):
        respnse = {
            'goal': args[1],
            'subgoals': [temp[args[2]], temp[args[3]]]
        }
        print(f"temp '{args[2]}' poped")
        temp.pop(args[2])
        print(f"temp '{args[3]}' poped")
        temp.pop(args[3])
        print("result:")
        return respnse
    print("temp Key Error")

def main():
    print("Add Entity, format type, x, y, [color, key], crtl + c/d to next, __q__ to exit")
    entities = []
    log = ""
    while True:
        try:
            line = input()
            if (line == "__q__"):
                exit()
            log += line + "\n"
            arg = line.replace(" ", "").split(",")
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
            entities.append(entity)
        except (ValueError, EOFError, KeyboardInterrupt):
            break
    temp = {}
    goals = []
    print("Add Goal, format command, args, ..., crtl + c/d to next, __q__ to exit")
    while True:
        try:
            line = input()
            if (line == "__q__"):
                exit()
            log += line + "\n"
            arg = line.split(",")
            if (len(arg) < 2):
                continue
            args = line.replace(" ", "").split(',')
            command = args[0]
            if (command == "g" and len(args) == 2):
                goals.append(goal(args[1]))
            elif (command == "s" and len(args) == 4):
                goals.append(supergoal(args[1], args[2], args[3]))
            elif (command == "c" and (len(args) == 3 or len(args) == 5)):
                complexCreateGoal(temp, line)
            elif (command == "m" and len(args) == 4):
                goals.append(complexMergeGoal(temp, line))
            else:
                print("Error input, try again")
                continue
        except (ValueError, EOFError, KeyboardInterrupt):
            break
    response = {
        'entities': entities,
        'goal-condition': goals,
    }
    # print(response)
    # print(log)
    save(response, log)


main()
