
import json
import random
import string
import time

import outcome



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


def save(dic, loging, filename):
    name = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5))
    name += str(time.time())
    with open(f'./src/test/resources/ExtraMap/{filename+name}.json', 'w') as f:
        json.dump(dic, f, indent=2)
    log = open(f'./src/test/resources/ExtraMap/{filename+name}.txt', 'w')
    log.write(loging)
    log.close()
    print(f"filename: {filename+name}")
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

def addmap(x, y, map, entityname):
    if x in map.keys():
        if y in map[x].keys():
            map[x][y].append(entityname)
        else:
            map[x][y] = []
            map[x][y].append(entityname)
    else:
        map[x] = {y: [entityname]}

def printmap(map):
    output = ""
    addition = ""
    dircs = """
      UP
LEFT  XXX  RIGHT
      DOWN
"""
    count = 1
    min_x = min(map.keys())
    min_y = min([min(ent.keys()) for ent in map.values()])
    max_x = max(map.keys())
    max_y = max([max(ent.keys()) for ent in map.values()])
    print("-" * (8 * (max_y - min_y)))
    # print(min_x)
    # print(min_y)
    # print(max_x)
    # print(max_y)
    for x in range(min_x, max_x + 1):
        line = ""
        for y in range(min_y, max_y + 1):
            word = ""
            if x in map.keys():
                if y in map[x].keys():
                    if len(map[x][y]) == 1:
                        if len(map[x][y][0]) > 6:
                            word = f"{map[x][y][0][:6]}"
                        else:
                            if int((6 - len(map[x][y][0]))/2) * 2 == (6 - len(map[x][y][0])):
                                word = f"{' ' * int((6 - len(map[x][y][0]))/2)}{map[x][y][0]}{' ' * int((6 - len(map[x][y][0]))/2)}"
                            else:
                                word = f"{' ' * int((6 - len(map[x][y][0]))/2)}{map[x][y][0]}{' ' * (int((6 - len(map[x][y][0]))/2)+1)}"

                    else:
                        count_str = str(count)
                        word = f"[{(4-len(count_str)) * '_'} {count_str}]" 
                        addition += f"{count}: "+', '.join(map[x][y])
                        count += 1
                else:
                    word = f"[{' ' * 4}]"
            else:
                word = f"[{' ' * 4}]"
            line += word + " "
        output += line + "\n"
    print(output + addition)
    print(dircs)
    return output + addition


            



def main():
    map = {}

    filename = input("filename: ")
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
            if ((arg[0] in ['door', 'key', 'portal', 'light_bulb_off', 'bomb'] and len(arg) != 4) or (arg[0] in ['switch_door'] and len(arg) != 5)):
                print("Error in input argument, lacking of 'color' in portal or 'key' in (door and key)")
                continue
            entity = newEntities(arg[0], int(arg[1]), int(arg[2]))
            addmap(int(arg[2]), int(arg[1]), map, arg[0])
            if (arg[0] == "portal"):
                entity['colour'] = arg[3]
            elif (arg[0] in ['door', 'key']):
                entity['key'] = int(arg[3])
            elif (arg[0] in ['light_bulb_off', 'bomb']):
                entity['logic'] = arg[3]
            elif (arg[0] in ['switch_door']):
                entity['logic'] = arg[3]
                entity['key'] = int(arg[4])
            elif (arg[0] in ['switch'] and len(arg[0]) == 4):
                entity['logic'] = arg[3]
            entities.append(entity)
        except (ValueError, EOFError, KeyboardInterrupt):
            break
    temp = {}
    goals = {}
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
                goals = goal(args[1])
            elif (command == "s" and len(args) == 4):
                goals = supergoal(args[1], args[2], args[3])
            elif (command == "c" and (len(args) == 3 or len(args) == 5)):
                complexCreateGoal(temp, line)
            elif (command == "m" and len(args) == 4):
                goals = complexMergeGoal(temp, line)
            else:
                print("Error input, try again")
                continue
        except (ValueError, EOFError, KeyboardInterrupt):
            break
    response = {
        'entities': entities,
        'goal-condition': goals,
    }
    printmap(map)
    # print(goals)
    save(response, log, filename)


main()
# temp= {}
# addmap(1,2,temp, "test")
# addmap(2,3,temp, "test")
# addmap(1,4,temp, "test")
# addmap(4,4,temp, "test")
# print(temp)
# printmap(temp)
